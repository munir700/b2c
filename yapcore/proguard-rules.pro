# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


#-keepclassmembers class *
#{
#  @com.leanplum.annotations.* <fields>;
#}
#-keep class com.leanplum.** { *; }
#-dontwarn com.leanplum.**


# Disable Android logging
-assumenosideeffects class android.util.Log {
     public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# This gets rid of System.out.println() and System.out.print()
# WARNING: if you're using this functions for other PrintStreams in your app, this can break things!
-assumenosideeffects class java.io.PrintStream {
    public void println(...);
    public void print(...);
}
