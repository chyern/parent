package com.chenyudan.parent.test;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/6/15 19:08
 */
@SpringBootTest(classes = {TestStartApplication.class})
@FixMethodOrder(MethodSorters.JVM)
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
}
