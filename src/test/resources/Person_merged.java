class Person
{
	@Generated
	private String firstName;
	@Generated
	private String lastName;
	@Generated
	private int    age;

	public String getFirstName()
	{
		return this.firstName == null ? "" : this.firstName;
	}

	public String getLastName()
	{
		return this.lastName + " Jr";
	}

	public String getFullName()
	{
		return firstName + " " + lastName;
	}

	@Generated
	public int getAge()
	{
		return this.age;
	}
}
