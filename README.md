# pokemon-tree
BAU 2018-2019 Spring Project. A program that builds images into a binary tree depending on their average colors.

## Using the program
In order to run the program you must first build it using [Maven](https://maven.apache.org/download.cgi). Don't forget to [set your JAVA_HOME](https://docs.oracle.com/cd/E19182-01/821-0917/6nluh6gq9/index.html) environment variable.

### Building the program
To build (package) the project you can run ```mvn package -f "<YOUR PATH TO pom.xml FILE>"``` or use your IDEs debugger tool.

### Running it
Building the project will result with a ``pokemon-tree.jar`` file inside the ``target`` folder. You also have to copy the ``data.json`` file into the ``target`` folder. Afterwards you can run the .jar file with ``java -jar pokemon-tree.jar`` command.