# Proguard 官方手册（https://www.guardsquare.com/en/products/proguard/manual）
指定压缩级别
-optimizationpasses 5
 
#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers
# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify
 
#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
 
#把混淆类中的方法名也混淆了
-useuniqueclassmembernames
# 保留Annotation不混淆 这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*,InnerClasses
#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification
# 列出从apk中删除的代码
-printusage unused.txt
#忽略警告
-ignorewarnings
#保留行号
-keepattributes SourceFile,LineNumberTable
#保持泛型
-keepattributes Signature
# 移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用
# 记得proguard-android.txt中一定不要加-dontoptimize才起作用
# 另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
 
#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#四大组件相关不能混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View
#native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}


