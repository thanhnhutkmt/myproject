import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Licensed to Open-Ones Group under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Open-Ones Group licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * @author nhutlt
 *
 */
public class InspectExample {
    private static String className = "String"; 
    private static String constructorName = "java.lang.String";
    private static String methodName = "length";
    private static String fieldName = "CASE_INSENSITIVE_ORDER";
    private static String innerClassName = "";
    private static String modifierString = "public final";
    private static String annotationsString = "";
    
    public static void main(String[] arg){
        // inspect method length() of String class
        String classname = "java.lang.String";
        String scn = null, smn = null, sfn = null, sicn = null, sm = null, sa = null;

        Class classToTest = null;
        try {
            classToTest = Class.forName(classname);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        Method[] methodSet = classToTest.getDeclaredMethods();
        Constructor[] constructorSet = classToTest.getConstructors();
        Field[] fieldSet = classToTest.getFields();
        String modifier = Modifier.toString(classToTest.getModifiers());
                
        for(Method a : methodSet){
            if (a.getName().equalsIgnoreCase(methodName)){
                smn = "co";
                break;
            }
            else {
                smn = "khong co";
            }
        }
        for(Constructor c : constructorSet){
            String temp = c.getName();
            if (c.getName().equalsIgnoreCase(constructorName)){
                scn = "co";
                break;
            }
            else{
                scn = "khong co";
            }
        }
        for(Field c : fieldSet){
            if (c.getName().equalsIgnoreCase(fieldName)){
                sfn = "co";
                break;
            }
            else{
                sfn = "khong co";
            }
        }
       
        System.out.format("Lop %s %s %s ham khoi dung %s() %s ham %s() %s thuoc tinh %s", modifierString, className, scn, constructorName, smn, methodName, sfn, fieldName);
    }
}
