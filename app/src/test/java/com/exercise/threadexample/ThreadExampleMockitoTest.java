package com.exercise.threadexample;

import org.junit.*;
import org.mockito.*;
import org.mockito.junit.*;

import static org.mockito.Mockito.*;

public class ThreadExampleMockitoTest {

    @Mock
    MainActivity mainActivity;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void test_MainActivity()  {
        mainActivity = Mockito.mock(MainActivity.class);
        when(mainActivity.getThreadCount()).thenReturn(3);



    }
}
