package org.springframework.learn.databind;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.DataBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link org.springframework.validation.DataBinder}示例
 */
public class DataBinderDemo {

	public static void main(String[] args) {
		try {

			Address address = new Address();
			DataBinder binder = new DataBinder(address, "address");
//			binder.setIgnoreUnknownFields(false);
//			binder.setAllowedFields("province", "city");
//			binder.setAutoGrowNestedPaths(false);
			binder.setIgnoreInvalidFields(false);

			Map<String, Object> properties = new HashMap<>();
			properties.put("province", "广东省");
			properties.put("city", "深圳市");
			properties.put("street", 1);

			// propertyValues中存在bean中不存在的属性值，DataBinder会会忽略未知的属性
			properties.put("phone", "xxxx");

			// propertyValues存在嵌套属性
//			properties.put("neighbourhood", new Neighbourhood());  // setAutoGrowNestedPaths
			properties.put("neighbourhood.name", "yyyyy");

			PropertyValues propertyValues = new MutablePropertyValues(properties);
			binder.bind(propertyValues);
			System.out.println(address);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	static class Address {
		private String province;

		private String city;

		private String street;

		private Neighbourhood neighbourhood;

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public Neighbourhood getNeighbourhood() {
			return neighbourhood;
		}

		public void setNeighbourhood(Neighbourhood neighbourhood) {
			this.neighbourhood = neighbourhood;
		}

		@Override
		public String toString() {
			return "Address{" +
					"province='" + province + '\'' +
					", city='" + city + '\'' +
					", street='" + street + '\'' +
					", neighbourhood=" + neighbourhood +
					'}';
		}
	}

	static class Neighbourhood {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Neighbourhood{" +
					"name='" + name + '\'' +
					'}';
		}
	}
}
