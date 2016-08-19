# GMath
The main functionality of GMath is to be used as a command line calculator, doing things that a regular calculator would not be able to do. GMath can also double as an external reference library if all classes except Runner are made into a JAR file.

##Build
Compile the java files to class files using the `javac` command. Then create a runnable JAR file containing the compiled class files with `Runner.class` as the location of the main method.

##Usage
Assuming the created JAR file is named gmath.jar and you have cd'd to its directory:
`java -jar gmath.jar <operation> [input]`
Or alternatively, put gmath.jar in C:\Users\(your_username)\Programs, put gmath.bat's folder in PATH, and run:
`gmath <operation> [input]`

To get usage information, type
`java -jar gmath.jar`
for possible operations, or
`java -jar gmath.jar <operation>`
for usage of a certain operation.
