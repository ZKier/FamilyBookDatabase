package model;


class PersonTest {
	
	public static void main(String args[]) {
		testPerson_contructor1();
		testPerson_contructor2();
		testGetFirstName();
		testGetMiddleName();
		testGetLastName();
		testGetParents();
		testGetChildren();
		testGetDateOfBirth();
		
		testSetFirstName();
		testSetmiddleName();
		testSetLastName();
		testSetParents();
		testSetChildren();
		testSetDateOfBirth();
	}

	
	static void testPerson_contructor1() {
		System.out.println("testPerson_constructer1");
		Person person1 = new Person();
		
		person1.setFirstName("Love");
		person1.setLastName("Powers");
		
		System.out.printf("%s %s\n" , person1.getFirstName(),  person1.getLastName());
	}

	
	static void testPerson_contructor2() {
		System.out.println("testPerson_contructor2");
		Person person1 = new Person("Grace", "Woodard");
		System.out.printf("%s %s\n", person1.getFirstName(), person1.getLastName());
	}

	static void testGetFirstName() {
		System.out.println("Not yet implemented");
	}

	static void testGetMiddleName() {
		System.out.println("Not yet implemented");
	}

	static void testGetLastName() {
		System.out.println("Not yet implemented");
	}

	static void testGetParents() {
		System.out.println("Not yet implemented");
	}

	static void testGetChildren() {
		System.out.println("Not yet implemented");
	}

	static void testGetDateOfBirth() {
		System.out.println("Not yet implemented");
	}

	static void testSetFirstName() {
		System.out.println("Not yet implemented");
	}

	static void testSetmiddleName() {
		System.out.println("Not yet implemented");
	}

	static void testSetLastName() {
		System.out.println("Not yet implemented");
	}

	static void testSetParents() {
		System.out.println("Not yet implemented");
	}

	static void testSetChildren() {
		System.out.println("Not yet implemented");
	}

	static void testSetDateOfBirth() {
		System.out.println("Not yet implemented");
	}

}
