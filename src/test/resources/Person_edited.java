class Person
{
	@Generated
	private String firstName;
	@Generated
	private String lastName;

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
}
