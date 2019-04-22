# pokemon-tree
BAU 2018-2019 Spring Project. A program that builds images into a binary tree depending on their average colors.

## Using the program
In order to run the program you must first build it using [Maven](https://maven.apache.org/download.cgi). Don't forget to [set your JAVA_HOME](https://docs.oracle.com/cd/E19182-01/821-0917/6nluh6gq9/index.html) environment variable.

### Building the program
After adding Maven to your PATH, you can use it in the CLI by running ``mvn``.
To build (package) the project you can run ```mvn install -f "<YOUR PATH TO pom.xml FILE>"``` or use your IDEs debugger tool. Installing the project will download the 3rd party libraries used within the project.

The project resorce files (files within the resources folder) will automatically be included in the .jar file. Meaning that it can be distributed as a standalone executable. 

### Running it
Building the project will result with a ``pokemon-tree.jar`` file inside the ``target`` folder. You can run the .jar file with the ``java -jar pokemon-tree.jar`` command.
