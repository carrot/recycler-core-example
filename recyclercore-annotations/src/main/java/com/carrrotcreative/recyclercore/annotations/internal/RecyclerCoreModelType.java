package com.carrrotcreative.recyclercore.annotations.internal;

import com.carrrotcreative.recyclercore.annotations.RecyclerCoreModel;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * Created by kaushiksaurabh on 11/2/15.
 */
public class RecyclerCoreModelType
{
    private String mCanonicalName;
    private String mSimpleName;
    private int mLayout;

    public RecyclerCoreModelType(Element element)
    {
        RecyclerCoreModel annotation = element.getAnnotation(RecyclerCoreModel.class);

        print("Test Message");

        mLayout = annotation.layout();

        try
        {
            Class<?> clazz = annotation.controller();
            mCanonicalName = clazz.getCanonicalName();
            mSimpleName = clazz.getSimpleName();
        }
        catch(MirroredTypeException exception)
        {
            DeclaredType dt = (DeclaredType) exception.getTypeMirror();
            TypeElement typeElement = (TypeElement) dt.asElement();
            mCanonicalName = typeElement.getQualifiedName().toString();
            mSimpleName = typeElement.getSimpleName().toString();
        }

        print("canonicalName=" + mCanonicalName + "::simpleName=" + mSimpleName);
    }

    @Override
    public int hashCode()
    {
        return mCanonicalName.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof RecyclerCoreModelType)
        {
            RecyclerCoreModelType newType = (RecyclerCoreModelType) obj;
            if(newType.getCanonicalName().equals(this.mCanonicalName))
            {
                return true;
            }
        }
        return false;
    }

    public String getCanonicalName()
    {
        return mCanonicalName;
    }

    public String getSimpleName()
    {
        return mSimpleName;
    }

    public int getLayout()
    {
        return mLayout;
    }

    private void print(String message)
    {
        System.out.println("**kaushik**::" + message);
    }
}
