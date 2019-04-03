#!/bin/sh
javac Main.java
jar -cvf program.jar Main.class
java -jar program.jar
jar cvf program.jar Main.class
jar cvmf MANIFEST.MF program.jar Main.class
zip -r program.zip program.jar
