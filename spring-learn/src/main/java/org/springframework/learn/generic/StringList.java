package org.springframework.learn.generic;

import java.util.ArrayList;

/**
 * ArrayList<String> ： 泛型参数具体化之后字节码中才会有记录，否则，获取泛型参数变量为null
 */
@SuppressWarnings("serial")
class StringList extends ArrayList<String> {

}
