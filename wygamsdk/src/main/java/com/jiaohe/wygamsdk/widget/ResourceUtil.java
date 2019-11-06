package com.jiaohe.wygamsdk.widget;

import android.content.Context;
import android.content.res.Resources;

import java.lang.reflect.Field;

/**
 * @package: com.jiaohe.wygamsdk.widget
 * @user:xhkj
 * @date:2019/10/31
 * @description:
 **/
public class ResourceUtil {
    public ResourceUtil() {

    }

    /**
     * 根据名字获取布局文件的id
     *
     * @param context
     * @param name    布局文件的名字
     * @return
     */
    public static int getLayoutIdByName(Context context, String name) {
        return getIdByName(context, "layout", name);
    }

    /**
     * 根据名字获取图片的id
     *
     * @param context
     * @param name    图片的名字
     * @return
     */
    public static int getDrawableIdByName(Context context, String name) {
        return getIdByName(context, "drawable", name);
    }

    /**
     * 根据名字获取string.xml文件中字符串的id
     *
     * @param context
     * @param name    字符串的名字
     * @return
     */
    public static int getStringIdByName(Context context, String name) {
        return getIdByName(context, "string", name);
    }

    /**
     * 根据名字获取布局文件中组件的id
     *
     * @param context
     * @param name    组件id属性的名字
     * @return
     */
    public static int getViewIdByName(Context context, String name) {
        return getIdByName(context, "id", name);
    }

    /**
     * 根据名字获取颜色的id
     *
     * @param context
     * @param name    颜色的名字
     * @return
     */
    public static int getColorIdByName(Context context, String name) {
        return getIdByName(context, "color", name);
    }

    /**
     * 根据名字获取尺寸（dimen）的id
     *
     * @param context
     * @param name    名字
     * @return
     */
    public static int getDimenIdByName(Context context, String name) {
        return getIdByName(context, "dimen", name);
    }

    /**
     * 根据资源类型和名字获取资源id
     *
     * @param context
     * @param className 资源类型，如"drawable"/"layout"/"id"/"string"等
     * @param name      资源的名字
     * @return
     */
    private static int getIdByName(Context context, String className, String name) {
        return context.getResources().getIdentifier(name, className, context.getPackageName());
    }

    private static Object getResourceId(Context context, String name, String type) {
        String className = context.getPackageName() + ".R";
        try {
            Class<?> cls = Class.forName(className);
            for (Class<?> childClass : cls.getClasses()) {
                String simple = childClass.getSimpleName();
                if (simple.equals(type)) {
                    for (Field field : childClass.getFields()) {
                        String fieldName = field.getName();
                        if (fieldName.equals(name)) {
                            System.out.println(fieldName);
                            return field.get(null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取styleable的ID号数组
     */
    public static int[] getStyleableArray(Context context,String name) {
        return (int[])getResourceId(context, name,"styleable");
    }

    /**
     *context.getResources().getIdentifier无法获取到styleable的数据
     */
    public static int getStyleable(Context context, String name) {
        return ((Integer)getResourceId(context, name,"styleable")).intValue();
    }

}
