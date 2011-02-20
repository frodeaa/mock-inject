## MockInjector

Set fields and single parameter methods that are annotated with @Inject. A simple utility
tool for testing CDI managed objects without needing to setting fields manually while testing,
or creating setters for all @Inject annotated fields.

Example:

	import static mock.inject.MockInject.into;
	
	  @Test
	  public void test() {
	 
	   into( bean )
	    .inject( mockA )
	    .injectWith( KindB.class, mockB );
	
	   // test the bean
	 }
	 
	class Bean {
	     @Inject 
	     private A a;
	     
	     private A b;
	  
	     @Inject
	     public setB( &#64;KindB A b ){...}
	}

The MockInject should probably only be used in tests...