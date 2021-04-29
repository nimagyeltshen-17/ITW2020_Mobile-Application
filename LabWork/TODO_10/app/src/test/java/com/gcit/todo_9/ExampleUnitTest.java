package com.gcit.todo_9;
import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
@SmallTest
public class ExampleUnitTest {
    private Calculator cal;

    @Before
    public void setUp(){
        cal = new Calculator();
    }

    @Test
    public void AddTwoNumber(){
        double result = cal.add(1d, 2d);
        assertThat(result, is(equalTo(3d)));
    }
    @Test
    public void SubTwoNumber(){
        double result = cal.sub(4d, 1d);
        assertThat(result, is(equalTo(3d)));
    }

    @Test
    public void SubWorksWithNegativeResults(){
        double result = cal.sub(-3d, 4d);
        assertThat(result, is(equalTo(-7d)));
    }

    @Test
    public void MulTwoNumber(){
        double result = cal.mul(4d, 5d);
        assertThat(result, is(equalTo(20d)));
    }

    @Test
    public void MulTwoNumbersZero(){
        double result = cal.mul(0d, 7d);
        assertThat(result, is(equalTo(0d)));
    }

    @Test
    public void DivTwoNumber(){
        double result = cal.div(13d, 13d);
        assertThat(result, is(equalTo(1d)));
    }

    @Test
    public void DivTwoNumbersZero(){
        double result = cal.div(4d, 0d);
        assertThat(result, is(equalTo(Double.POSITIVE_INFINITY)));
    }

    @Test
    public void AddTwoNumberNegative(){
        double result = cal.add(-2d, 4d);
        assertThat(result, is(equalTo(2d)));
    }

    @Test
    public void AddTwoNumbersFloats() {
        double result = cal.add(1.111f, 1.111d);
        assertThat(result, is(closeTo(2.222, 0.01)));
    }
}
