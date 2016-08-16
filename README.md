# GMath
The main functionality of GMath is to be used as a command line calculator, doing things that a regular calculator would not be able to do. The Quadratic and GMath classes can also double as external reference libraries if individuaclly made into JAR files. However, it is important to note that Quadratic is dependant on GMath functions, therefore they must be imported together if you are looking to use Quadratic in your own program. I am looking into a way to work around this as cleanly as possible.

##Build
Compile the java files to class files using the `javac` command. Then create a runnable JAR file containing the compiled class files with `Runner.class` as the location of the main method.

##Usage
Assuming the created JAR file is named gmath.jar:
`java -jar gmath.jar <operation> [input]`

To get usage information, type
`java -jar gmath.jar`
for possible operations, or
`java -jar gmath.jar <operation>`
for usage of a certain operation.
