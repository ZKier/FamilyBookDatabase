package model;

//import java.time.LocalDate;

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
		testGetDateOfBirthDay();
		//testGetFormattedDateOfBirth();
		testGetBiography();
		
		testSetFirstName();
		testSetMiddleName();
		testSetLastName();
		testSetParents();
		testSetChildren();
		testSetDateOfBirth();
		testSetBiography();

		testPersonEquals();
		testPersonEqualsWithParent();
		testPersonEqualsWithChild();
		testPersonNotEqualWithChild();

	}

	
	static void testPerson_contructor1() {
		System.out.println("testPerson_constructer1");
		Person person1 = new Person();
		
		person1.setFirstName("Love");
		person1.setLastName("Powers");
		
		System.out.printf("%s %s\n\n" , person1.getFirstName(),  person1.getLastName());
	}

	
	static void testPerson_contructor2() {
		System.out.println("testPerson_contructor2");
		Person person1 = new Person("Grace", "Woodard");
		System.out.printf("%s %s\n\n", person1.getFirstName(), person1.getLastName());
	}

	static void testGetFirstName() {
		System.out.println("testGetFirstName");
		Person person1 = new Person("Kacey", "Monster");
		System.out.println("Expected: Kacey");
		System.out.printf("Outcome: %s\n\n", person1.getFirstName());
	}

	static void testGetMiddleName() {
		System.out.println("testGetMiddleName");
		Person person1 = new Person("Kacey", "Monster");
		System.out.println("Expected: N/A");
		System.out.printf("Outcome: %s\n\n", person1.getMiddleName());
	}

	static void testGetLastName() {
		System.out.println("testGetLastName");
		Person person1 = new Person("Kacey", "Monster");
		System.out.println("Expected: Monster");
		System.out.printf("Outcome: %s\n\n", person1.getLastName());
	}

	static void testGetParents() {
		System.out.println("testGetParents");
		Person person1 = new Person("Kacey", "Monster");
		System.out.println("Expected: 0");
		System.out.printf("Outcome: %d\n\n", person1.getParentsCount());
	}

	static void testGetChildren() {
		System.out.println("testGetChildren");
		Person person1 = new Person("Kacey", "Monster");
		System.out.println("Expected: 0");
		System.out.printf("Outcome: %d\n\n", person1.getChildrenCount());
	}

	static void testGetDateOfBirth() {
		System.out.println("testGetDateOfBirth");
		Person person1 = new Person("Kacey", "Monster");
		System.out.println("Expected: 1999-02-21");
		System.out.printf("Outcome: %s\n\n", person1.getDateOfBirth().toString());
		//System.out.println(person1.getDateOfBirth().getMonthValue());
	}
	static void testGetDateOfBirthDay() {
		System.out.println("testGetDateOfBirthDay");
		Person person1 = new Person("Kacey", "Monster");
		System.out.println("Expected: 21");
		System.out.printf("Outcome: %s\n\n", person1.getDateOfBirth().toString().substring(8, 10));
		//System.out.println(person1.getDateOfBirth().getMonthValue());
	}
/*
	static void testGetFormattedDateOfBirth() {
		System.out.println("testGetFormattedDateOfBirth");
		Person person1 = new Person("Kacey", "Monster");
		System.out.println("Expected: 2/21/1999");
		System.out.printf("Outcome: %s\n\n", person1.getFormattedDateOfBirth());
	}
*/
	static void testGetBiography() {
		System.out.println("testGetBiography");
		Person person1 = new Person("Kacey", "Michelle", "Monster", 4, 3, 2001, "Monster High Prom Queen");
		System.out.println("Expected: Monster High Prom Queen");
		System.out.printf("Outcome: %s\n\n", person1.getBiography());

	}

	static void testSetFirstName() {
		System.out.println("testSetFirstName");
		Person person1 = new Person("Kacey", "Michelle", "Monster", 4, 3, 2001, "Monster High Prom Queen");
		person1.setFirstName("Kelly");
		System.out.println("Expected: Kelly");
		System.out.printf("Outcome: %s\n\n", person1.getFirstName());
	}

	static void testSetMiddleName() {
		System.out.println("testSetMiddleName");
		Person person1 = new Person("Kacey", "Michelle", "Monster", 4, 3, 2001, "Monster High Prom Queen");
		person1.setMiddleName("Mikky");
		System.out.println("Expected: Mikky");
		System.out.printf("Outcome: %s\n\n", person1.getMiddleName());
	}

	static void testSetLastName() {
		System.out.println("testSetLastName");
		Person person1 = new Person("Kacey", "Michelle", "Monster", 4, 3, 2001, "Monster High Prom Queen");
		person1.setLastName("Screech");
		System.out.println("Expected: Screech");
		System.out.printf("Outcome: %s\n\n", person1.getLastName());
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

	static void testSetBiography() {
		System.out.println("testSetBiography");
		Person person1 = new Person("Kacey", "Michelle","Monster", 4,26,1999,"lover of animals");
		person1.setBiography("Monster High Prom Queen");
		System.out.println("Expected: Monster High Prom Queen");
		System.out.printf("Outcome: %s\n\n", person1.getBiography());

	}

	static void testPersonEquals() {
		System.out.println("testPersonEquals");
		Person person1 = new Person("Kacey", "Michelle","Monster", 4,26,1999,"lover of animals");
		Person person2 = new Person("Kacey", "Michelle","Monster", 4,26,1999,"lover of animals");
		System.out.println("Expected: true");
		System.out.printf("Outcome: %b\n\n", person1.equals(person2));
		//System.out.println("Person1 = " + person1.getFirstName() + " Person2 = " + person2.getFirstName());
	}

	static void testPersonEqualsWithParent() {
		System.out.println("testPersonEqualsWithParent");
		Person person1 = new Person("Kacey", "Michelle","Monster", 4,26,1999,"lover of animals");
		Person person2 = new Person("Kacey", "Michelle","Monster", 4,26,1999,"lover of animals");
		Person parent1 = new Person("Kimmy", "Michelle","Monster", 4,26,1999,"lover of animals");
		person1.addParent(parent1);
		person2.addParent(parent1);
		System.out.println("Expected: true");
		System.out.printf("Outcome: %b\n\n", person1.equals(person2));
		//System.out.println("Person1 = " + person1.getFirstName() + " Person2 = " + person2.getFirstName());
	}

	static void testPersonEqualsWithChild() {
		System.out.println("testPersonEqualsWithChild");
		Person person1 = new Person("Kacey", "Michelle", "Monster", 4, 26, 1999, "lover of animals");
		Person person2 = new Person("Kacey", "Michelle", "Monster", 4, 26, 1999, "lover of animals");
		Person child1 = new Person("Kimmy", "Michelle", "Monster", 4, 26, 1999, "lover of animals");
		person1.addParent(child1);
		person2.addParent(child1);
		System.out.println("Expected: true");
		System.out.printf("Outcome: %b\n\n", person1.equals(person2));
		//System.out.println("Person1 = " + person1.getFirstName() + " Person2 = " + person2.getFirstName());
	}

	static void testPersonNotEqualWithChild() {
		System.out.println("testPersonNotEqualWithChild");
		Person person1 = new Person("Kacey", "Michelle", "Monster", 4, 26, 1999, "lover of animals");
		Person person2 = new Person("Kacey", "Michelle", "Monster", 4, 26, 1999, "lover of animals");
		Person child1 = new Person("Kimmy", "Michelle", "Monster", 4, 26, 1999, "lover of animals");
		person1.addParent(child1);
		//person2.addParent(child1);
		System.out.println("Expected: false");
		System.out.printf("Outcome: %b\n\n", person1.equals(person2));
		//System.out.println("Person1 = " + person1.getFirstName() + " Person2 = " + person2.getFirstName());
	}

	static void testPersonEqualWithChild() {
		System.out.println("testPersonNotEqualWithChild");
		Person person1 = new Person("Kacey", "Michelle", "Monster", 4, 26, 1999, "lover of animals");
		Person person2 = new Person("Kacey", "Michelle", "Monster", 4, 26, 1999, "lover of animals");
		Person child1 = new Person("Kimmy", "Michelle", "Monster", 4, 26, 1999, "lover of animals");
		person1.addParent(child1);
		//person2.addParent(child1);
		System.out.println("Expected: false");
		System.out.printf("Outcome: %b\n\n", person1.equals(person2));
		//System.out.println("Person1 = " + person1.getFirstName() + " Person2 = " + person2.getFirstName());
	}

}
