-dontnote **
-dontwarn **

-obfuscationdictionary ../../字典.txt
-classobfuscationdictionary ../../字典.txt
-packageobfuscationdictionary ../../字典.txt

-keep class com.** {*;}

-keepclasseswithmembernames class * {
native <methods>;
}

-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements java.io.Serializable {
static final long serialVersionUID;
private static final java.io.ObjectStreamField[] serialPersistentFields;
private void writeObject(java.io.ObjectOutputStream);
private void readObject(java.io.ObjectInputStream);
java.lang.Object writeReplace();
java.lang.Object readResolve();
}


-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}
