The program prints out an overview of cross-links within an XML document formated as a DocBook. The program reads the DocBook document from the standard input and to the standard output, it prints out an overview of cross-links with names of sections among which the cross-links are set.

The cross-links are created via the <link> element. The cross-link target is a unique ID in the \texttt{linkend} attribute. The document is structured into section that are formed by the <section> element. Each section has its title specified in the <title> element. Sections can be arbitrarily nested. Cross-links are always in a section; the same is true for cross-links targets.

The program prints out a list of cross-links grouped by a section, in which the cross-link is located. For each of the cross-links, the program prints out a text of the cross-link and the title of a section, in which the cross-link target is located (the cross-link is indented by 4 spaces). For example, if there is a cross-link with the text "see in section ABC" from section "XYZ" to section "ABC", then the program prints out

XYZ:
    see in section ABC (ABC)

The size of the input documents can be up to tens of megabytes. We presume the input document is always a valid XML and has the above described structure. The XML document does not contain any namespaces (i.e., all the elements and attributes are without any prefix).
