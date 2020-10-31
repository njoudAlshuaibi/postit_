package com.postit.postit_.Student_Visitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.SharedPreferences;
public class sharedPreference {

        private static SharedPreferences prf;

        private sharedPreference(){
        }//end of MySharedPreference

        public static SharedPreferences getInstance(Context context){
            if(prf==null){
                prf=context.getSharedPreferences(Constants.keys.USER_DETAILS,Context.MODE_PRIVATE);
            }//end of if statement
            return prf;
        }

        public static void clearData(Context context){
            SharedPreferences.Editor editor=getInstance(context).edit();
            editor.clear();
            editor.commit();

        }//end of clearData

        public static void clearValue(Context context,String key){
            SharedPreferences.Editor editor=getInstance(context).edit();
            editor.remove(key);
            editor.apply();
        }

        public static void putString(Context context,String key,String value){
            SharedPreferences.Editor editor=getInstance(context).edit();
            editor.putString(key,value);
            editor.apply();
        }

        public static void putInt(Context context,String key,int value){
            SharedPreferences.Editor editor=getInstance(context).edit();
            editor.putInt(key,value);
            editor.apply();
        }


        public static void putBoolean(Context context,String key,boolean value){
            SharedPreferences.Editor editor=getInstance(context).edit();
            editor.putBoolean(key,value);
            editor.apply();
        }

        public static String getString(Context context,String key,String valueDefault){
            return getInstance(context).getString(key,valueDefault);
        }

        public static int getInt(Context context,String key,int valueDefault){
            return getInstance(context).getInt(key,valueDefault);
        }
        public static boolean getBoolean(Context context,String key,boolean valueDefault){
            return getInstance(context).getBoolean(key,valueDefault);
        }

    }//end of the class

