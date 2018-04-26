package io.jenkins.plugins.analysis.core.model;

import javax.naming.ldap.ExtendedRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.function.Function;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link PropertyStatistics}
 *
 * @author Elvira Hauer
 */
class PropertyStatisticsTest {

    private PropertyStatistics sut;
    private String property;
    private Map<String, Issues<Issue>> issuesByProperty;
    private Function<String, String> propertyFormatter;
    private Issues<Issue> testProperty;

    @BeforeEach
    void initialize(){
        testProperty = new Issues<>();

        issuesByProperty = new HashMap<>();
        issuesByProperty.put("FirstIssue", testProperty);
        //when(issuesByProperty.get(any())).thenReturn(testProperty);

        Issues<Issue> issues = mock(Issues.class);
        when(issues.groupByProperty(any())).thenReturn(issuesByProperty);
        when(issues.size()).thenReturn(1);


        property = "AProperty";
        propertyFormatter = mock(Function.class);
        when(propertyFormatter.apply(any())).thenReturn("TheDisplayName");
        sut = new PropertyStatistics(issues, property, propertyFormatter);
    }

    @Test
    void testGetTotal(){
        int testResult = sut.getTotal();

        assertThat(testResult).isEqualTo(1);
    }

    @Test
    void testGetProperty(){
        String testResult = sut.getProperty();

        assertThat(testResult).isEqualTo(property);
    }

    @Test
    void testGetDisplayName(){
        String testResult = sut.getDisplayName("AString");

        verify(propertyFormatter).apply("AString");
        verifyNoMoreInteractions(propertyFormatter);
        assertThat(testResult).isInstanceOf(String.class);
        assertThat(testResult).isEqualTo("TheDisplayName");
    }

    @Test
    void testGetKeys(){
        Set<String> testResult = sut.getKeys();

        verify(issuesByProperty).keySet();
        verifyNoMoreInteractions(issuesByProperty);
        assertThat(testResult).isInstanceOf(Set.class);
    }

    @Test
    void testGetMax(){
        int testResult = sut.getMax();

        verify(issuesByProperty).values();
        verifyNoMoreInteractions(issuesByProperty);

        assertThat(testResult).isInstanceOf(int.class);

    }

    @Test
    void testGetCount(){
        long testResult = sut.getCount("AKeySting");

        verify(issuesByProperty).get("AKeySting");
        verify(testProperty).size();
        verifyNoMoreInteractions(issuesByProperty);
        verifyNoMoreInteractions(testProperty);

        assertThat(testResult).isInstanceOf(long.class);

    }

    @Test
    void testGetHighCount(){
        long testResult = sut.getHighCount("AKeySting");

        verify(issuesByProperty).get("AKeySting");
        verify(testProperty).getHighPrioritySize();
        verifyNoMoreInteractions(issuesByProperty);
        verifyNoMoreInteractions(testProperty);

        assertThat(testResult).isInstanceOf(long.class);

    }

    @Test
    void testGetNormalCount(){
        long testResult = sut.getNormalCount("AKeySting");

        verify(issuesByProperty).get("AKeySting");
        verify(testProperty).getNormalPrioritySize();
        verifyNoMoreInteractions(issuesByProperty);
        verifyNoMoreInteractions(testProperty);

        assertThat(testResult).isInstanceOf(long.class);

    }

    @Test
    void testGetLowCount(){
        long testResult = sut.getLowCount("AKeySting");

        verify(issuesByProperty).get("AKeySting");
        verify(testProperty).getLowPrioritySize();
        verifyNoMoreInteractions(issuesByProperty);
        verifyNoMoreInteractions(testProperty);

        assertThat(testResult).isInstanceOf(long.class);

    }
}
