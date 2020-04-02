package se.crisp.specification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GherkinToHtmlConverterTest {

    private static final String FEATURE_1 = "Feature: Here is to you";
    private static final String FEATURE_PARAGRAPH_1 = "feature paragraph";
    private static final String FEATURE_PARAGRAPH_2 = "When there is a feature paragraph";
    private static final String SCENARIO_1 = "  Scenario: some scenario this is";
    private static final String GIVEN_STEP = "   Given some step";
    private static final String WHEN_STEP = "   When some step";
    private static final String THEN_STEP = "   Then some step";
    private static final String AND_STEP = "   And some step";
    private static final String BUT_STEP = "   But some step";
    private static final String TAG = "@1.0";
    private static final String STEP_END = "<br/>";

    @Test
    void parse_feature() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();

        String feature = converter.toHtml(FEATURE_1);

        assertEquals("<h1>" + FEATURE_1 + "</h1>", feature);
    }

    @Test
    void parse_feature_and_paragraph() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();
        converter.toHtml(FEATURE_1);

        String featureParagraph = converter.toHtml(FEATURE_PARAGRAPH_1);

        assertEquals("<p>" + FEATURE_PARAGRAPH_1, featureParagraph);
    }

    @Test
    void parse_feature_and_paragraph_with_keyword() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();
        converter.toHtml(FEATURE_1);

        String featureParagraph = converter.toHtml(FEATURE_PARAGRAPH_2);

        assertEquals("<p>" + FEATURE_PARAGRAPH_2, featureParagraph);
    }

    @Test
    void parse_feature_followed_by_scenario() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();
        converter.toHtml(FEATURE_1);

        String scenario = converter.toHtml(SCENARIO_1);

        assertEquals("<h2>" + SCENARIO_1.trim() + "</h2>", scenario);
    }

    @Test
    void parse_feature_followed_by_blank_line() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();
        converter.toHtml(FEATURE_1);
        converter.toHtml(FEATURE_PARAGRAPH_1);

        String scenario = converter.toHtml(" ");

        assertEquals("</p>", scenario);
    }

    @Test
    void parse_given_step() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();

        String step = converter.toHtml(GIVEN_STEP);

        assertEquals(GIVEN_STEP + STEP_END, step);
    }

    @Test
    void parse_when_step() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();

        String step = converter.toHtml(WHEN_STEP);

        assertEquals(WHEN_STEP + STEP_END, step);
    }

    @Test
    void parse_then_step() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();

        String step = converter.toHtml(THEN_STEP);

        assertEquals(THEN_STEP + STEP_END, step);
    }


    @Test
    void parse_and_step() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();

        String step = converter.toHtml(AND_STEP);

        assertEquals(AND_STEP + STEP_END, step);
    }

    @Test
    void parse_but_step() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();

        String step = converter.toHtml(BUT_STEP);

        assertEquals(BUT_STEP + STEP_END, step);
    }

    @Test
    void parse_tag() {
        GherkinToHtmlConverter converter = new GherkinToHtmlConverter();

        String tag = converter.toHtml(TAG);

        assertEquals(TAG + STEP_END, tag);
    }
}