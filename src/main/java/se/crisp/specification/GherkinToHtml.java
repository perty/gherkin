package se.crisp.specification;

/**
 * Read Gherkin files and create one big HTML output.
 */
public class GherkinToHtml {
    public static void main(String[] args) {
        for (String arg : args) {
            new GherkinToHtmlConverter(arg);
        }

    }
}
