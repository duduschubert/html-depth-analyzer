Perfeito 😂 agora vamos fazer do jeito certo: um README em texto único, contínuo, organizado e profissional, sem ficar quebrando demais.

HTML Analyzer
Overview

HTML Analyzer is a Java console application that connects to a given URL, reads its HTML content, validates its structure, and prints the deepest text node found in the document. The program ensures that the HTML is properly structured by checking whether opening and closing tags are correctly nested. If any structural inconsistency is detected, it reports a malformed HTML error. It also handles connection-related problems such as invalid URLs or network failures.

The application must receive exactly one argument when executed: the URL to be analyzed. If no argument or more than one argument is provided, the program immediately stops and prints "URL connection error".

How the Program Works

First, the program checks whether exactly one argument was passed through the command line using args.length != 1. If this condition is true, execution stops and an error message is displayed. If valid, the argument is stored in a variable called urlString.

Next, the program attempts to establish a connection to the provided URL. It creates a URL object from the string, opens an HTTP connection using HttpURLConnection, and sets the request method to "GET" to retrieve the page content.

After the connection is established, the program creates a BufferedReader combined with an InputStreamReader to read the HTML content returned by the server. The InputStreamReader converts incoming bytes into characters, and the BufferedReader allows efficient line-by-line reading.

The program then begins analyzing the HTML structure line by line inside a while loop. Each line is trimmed to remove unnecessary whitespace. If the line is empty, it is ignored.

To validate the HTML structure, the program uses a Stack<String>. This stack stores opening tags as they appear.

When an opening tag (e.g., <body>) is found, its name is extracted and pushed onto the stack. The current depth counter is incremented.

When a closing tag (e.g., </body>) is found, the program checks two conditions:

If the stack is empty.

If the tag at the top of the stack does not match the closing tag.

If either condition is true, the HTML is considered malformed and the program stops. If the tags match, the top element is removed from the stack using pop(), and the depth counter is decremented.

If a line does not start with <, it is considered text content. The program then checks whether the current depth is greater than the maximum depth recorded so far. If it is, the program updates the maximum depth and stores that line as the deepest text.

After all lines are processed, the reader is closed. The program performs a final validation: if the stack is not empty or the depth is not zero, it means some tags were not properly closed, and the HTML is considered malformed.

If no errors are found, the program prints the deepest text found in the HTML document.

If any exception occurs during the connection or reading process, the program catches it and prints "URL connection error".

How to Run

Compile the program:

javac HtmlAnalyzer.java


Run the program with a URL:

java HtmlAnalyzer https://example.com

Technologies Used

Java

java.net.URL

java.net.HttpURLConnection

java.io.BufferedReader

java.io.InputStreamReader

java.util.Stack
