-dontwarn rx.**

-keep class android.support.v7.widget.LinearLayoutManager {
    public protected *;
}

-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep class io.shtanko.picasagallery.data.model.**  { *; }

-keepattributes *Annotation*,Signature,Exceptions

-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

-dontwarn org.apache.lang.**
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }

-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-dontwarn dagger.internal.codegen.**
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}

-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection

-keep class com.google.common.base.Preconditions { *; }
-keep class android.support.v4.util.Precondition { *; }
-keep class dagger.internal.Preconditions { *; }
-dontwarn javax.annotation.**
-dontwarn javax.inject.**

# Proguard rules that are applied to your test apk/code.
-ignorewarnings

-keep class org.apache.http.** { *; }
-keep class android.net.http.** { *; }

-dontnote android.net.http.*
-dontnote org.apache.http.**
-dontwarn com.google.errorprone.annotations.*
-dontnote sun.misc.Unsafe
-dontnote dagger.internal.Binding
-dontnote dagger.internal.ModuleAdapter
-dontnote dagger.internal.StaticInjection