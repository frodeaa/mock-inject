## MockInject

Set fields and single parameter methods that are annotated with @Inject. A simple utility
tool for testing CDI managed objects without needing to set fields manually while testing,
and creating setters for all @Inject annotated fields.

Example:

	import static mock.inject.MockInject.into;
	
	  @Test
	  public void test() {
	 
	   into( bean )
	    .inject( mockA, mockB )
	    .injectWith( KindB.class, mockA );
	
	   // test the bean
	 }
	 
	class Bean {
	     @Inject 
	     private A a;
	
	     @Inject
	     private C c:
	     	  
	     @Inject
	     public setB( @KindB A b ){...}
	}

The MockInject should probably only be used in tests...

Maven dependency:

	<dependency>
		<groupId>mock-inject</groupId>
		<artifactId>mock-inject</artifactId>
		<version>0.1</version>
	</dependency>
