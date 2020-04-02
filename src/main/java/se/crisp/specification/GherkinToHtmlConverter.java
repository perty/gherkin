package se.crisp.specification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@SuppressWarnings("squid:S106")
public class GherkinToHtmlConverter {

    private static final String PREAMBLE = "<html><body>";
    private static final String POSTAMBLE = "</body></html>";
    private ParsingMode mode = ParsingMode.INITIAL;

    public GherkinToHtmlConverter(String fileName) {
        System.out.println(PREAMBLE);
        filesAndDirs(fileName);
        System.out.println(POSTAMBLE);
    }

    private void filesAndDirs(String fileName) {
        File file = new File(fileName);
        if (file.isDirectory()) {
            for (String fileOrDir : file.list()) {
                filesAndDirs(fileName + "/" + fileOrDir);
            }
        } else {
            if (fileName.endsWith(".feature")) {
                handleFile(fileName);
            }
        }
    }

    private void handleFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.lines().map(this::toHtml).forEach(System.out::println);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    GherkinToHtmlConverter() {
        // for testing
    }

    // Build a string and send out when complete
    String toHtml(String line) {
        LineType lineType = parseLine(line);
        switch (lineType) {
            case FEATURE:
                this.mode = ParsingMode.PARSING_FEATURE;
                return "<h1>" + line + "</h1>";
            case FEATURE_PARAGRAPH:
                if(this.mode == ParsingMode.PARSING_FEATURE) {
                    this.mode = ParsingMode.PARSING_FEATURE_PARAGRAPH;
                    return "<p>" + line;
                }
                return line;
            case SCENARIO:
                this.mode = ParsingMode.PARSING_SCENARIO;
                return "<h2>" + line.trim() + "</h2>";
            case BLANK_LINE:
                if (this.mode == ParsingMode.PARSING_FEATURE_PARAGRAPH) {
                    this.mode = ParsingMode.PARSING_FEATURE;
                    return "</p>";
                }
                return "";
            case GIVEN:
            case WHEN:
            case THEN:
            case AND:
            case BUT:
                if(this.mode == ParsingMode.PARSING_FEATURE) {
                    this.mode = ParsingMode.PARSING_FEATURE_PARAGRAPH;
                    return "<p>" + line;
                }
                if (this.mode == ParsingMode.PARSING_FEATURE_PARAGRAPH) {
                    return line;
                }
                this.mode = ParsingMode.STEP;
                return line + "<br/>";
            case TAG:
                return line + "<br/>";
        }
        return "Error: not parsed.";
    }

    private LineType parseLine(String line) {
        if (line.startsWith("@")) {
            return LineType.TAG;
        }
        if (line.trim().startsWith("Feature:")) {
            return LineType.FEATURE;
        }
        if (line.trim().startsWith("Scenario:")) {
            return LineType.SCENARIO;
        }
        if (line.trim().startsWith("Given")) {
            return LineType.GIVEN;
        }
        if (line.trim().startsWith("When")) {
            return LineType.WHEN;
        }
        if (line.trim().startsWith("Then")) {
            return LineType.THEN;
        }
        if (line.trim().startsWith("And")) {
            return LineType.AND;
        }
        if (line.trim().startsWith("But")) {
            return LineType.BUT;
        }
        if (line.trim().length() == 0) {
            return LineType.BLANK_LINE;
        }
        if (this.mode == ParsingMode.PARSING_FEATURE || this.mode == ParsingMode.PARSING_FEATURE_PARAGRAPH) {
            return LineType.FEATURE_PARAGRAPH;
        }
        throw new IllegalArgumentException("Could not parse '" + line + "'");
    }
}
