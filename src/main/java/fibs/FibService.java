package fibs;

import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyClass;
import org.jruby.RubyModule;
import org.jruby.RubyObject;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.BasicLibraryService;

public class FibService implements BasicLibraryService {

	Ruby runtime;
	
	@Override
	public boolean basicLoad(Ruby runtime) throws IOException {
		this.runtime = runtime;
		 	RubyModule mSequence = runtime.defineModule("Sequence");
		 	RubyClass webSocket = mSequence.defineClassUnder("SequenceMask", runtime.getObject(), new ObjectAllocator() {
		 	      public IRubyObject allocate(Ruby runtime, RubyClass rubyClass) {
		 	        return new Sequence(runtime, rubyClass);
		 	      }
		 	    });

		 	    webSocket.defineAnnotatedMethods(Sequence.class);
		return false;
	}
	
	public class Sequence extends RubyObject {
	    public Sequence(final Ruby runtime, RubyClass rubyClass) {
	      super(runtime, rubyClass);
	    }

	    @JRubyMethod
	    public IRubyObject mask(ThreadContext context, IRubyObject payload, IRubyObject mask) {
	      int n = ((RubyArray)payload).getLength(), i;
	      long p, m;
	      RubyArray unmasked = RubyArray.newArray(runtime, n);

	      long[] maskArray = {
	        (Long)((RubyArray)mask).get(0),
	        (Long)((RubyArray)mask).get(1),
	        (Long)((RubyArray)mask).get(2),
	        (Long)((RubyArray)mask).get(3)
	      };

	      for (i = 0; i < n; i++) {
	        p = (Long)((RubyArray)payload).get(i);
	        m = maskArray[i % 4];
	        unmasked.set(i, p ^ m);
	      }
	      return unmasked;
	    }
	  }

}
