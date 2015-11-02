package com.carrotcreative.recyclercore.compiler;

import com.carrrotcreative.recyclercore.annotations.RecyclerCoreModel;
import com.carrrotcreative.recyclercore.annotations.internal.RecyclerCoreModelType;
import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class RecyclerCoreProcessor extends AbstractProcessor
{
    private Types mTypeUtils;
    private Elements mElementUtils;

    /**
     * Used for display error messages.
     */
    private Messager mMessager;

    /**
     * Allows us to create files
     */
    private Filer mFiler;

    /**
     * If the processor class is annotated with {@link
     * SupportedAnnotationTypes}, return an unmodifiable set with the
     * same set of strings as the annotation.  If the class is not so
     * annotated, an empty set is returned.
     * <p/>
     * Here you have to specify for which annotations this annotation processor should be
     * registered for. Note that the return type is a set of strings containing full qualified
     * names for your annotation types you want to process with this annotation processor. In
     * other words, you define here for which annotations you register your annotation processor.
     *
     * @return the names of the annotation types supported by this
     * processor, or an empty set if none
     */
    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        Set<String> types = new LinkedHashSet<>();
        types.add(RecyclerCoreModel.class.getCanonicalName());
        return types;
    }

    /**
     * If the processor class is annotated with {@link
     * SupportedSourceVersion}, return the source version in the
     * annotation.  If the class is not so annotated, {@link
     * SourceVersion#RELEASE_6} is returned.
     *
     * @return the latest source version supported by this processor
     */
    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        return SourceVersion.latestSupported();
    }

    /**
     * Initializes the processor with the processing environment by
     * setting the {@code processingEnv} field to the value of the
     * {@code processingEnv} argument.  An {@code
     * IllegalStateException} will be thrown if this method is called
     * more than once on the same object.
     *
     * @param processingEnv environment to access facilities the tool framework
     *                      provides to the processor
     * @throws IllegalStateException if this method is called more than once.
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv)
    {
        super.init(processingEnv);
        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
    }

    /**
     * {@inheritDoc}
     *
     * @param annotations
     * @param roundEnv
     * @return true if you are claiming the annotation, false otherwise.
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        Set<RecyclerCoreModelType> recyclerCoreModelTypeSet = new HashSet<>();
        for(Element classElement : roundEnv.getElementsAnnotatedWith(RecyclerCoreModel.class))
        {
            if(classElement.getKind() != ElementKind.CLASS)
            {
                error(classElement, "Only classes can be annotated with " + RecyclerCoreModel.class.getSimpleName());
                return true;
            }

            if(! classElement.getModifiers().contains(Modifier.PUBLIC))
            {
                error(classElement, "The class " + classElement.getSimpleName().toString() + " is not public.");
                return true;
            }

            if(classElement.getModifiers().contains(Modifier.ABSTRACT))
            {
                error(classElement, "The class " + classElement.getSimpleName().toString() + " is abstract.");
                return true;
            }

            RecyclerCoreModelType recyclerCoreModelType = new RecyclerCoreModelType(classElement);
            recyclerCoreModelTypeSet.add(recyclerCoreModelType);
        }
        generateClass(recyclerCoreModelTypeSet);
        return false;
    }

    private void error(Element element, String msg)
    {
        mMessager.printMessage(Diagnostic.Kind.ERROR, msg, element);
    }

    private void print(String message)
    {
        System.out.println("**kaushik**::" + message);
    }

    private void generateClass(Set<RecyclerCoreModelType> recyclerCoreModelTypes)
    {

    }
}
