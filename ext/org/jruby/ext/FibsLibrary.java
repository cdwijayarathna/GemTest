package org.jruby.ext;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.RubyException;
import org.jruby.RubyKernel;
import org.jruby.RubyModule;
import org.jruby.RubyObject;
import org.jruby.anno.JRubyMethod;
import org.jruby.anno.JRubyClass;
import org.jruby.exceptions.RaiseException;
import org.jruby.runtime.Block;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.Visibility;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.Library;
import org.jruby.javasupport.JavaEmbedUtils;

/**
 * This library adds reference queue support to JRuby's weakrefs by adding a
 * RefQueue class that wraps a Java ReferenceQueue and replacing the built-in
 * WeakRef impl with a new one that's aware of RefQueue.
 * 
 * @author headius
 */
public class FibsLibrary implements Library {
    public void load(Ruby runtime, boolean wrap) throws IOException {
        // only used for RefError
        RubyKernel.require(runtime.getKernel(), runtime.newString("weakref"), Block.NULL_BLOCK);
        RubyModule fibsModule = runtime.getOrCreateModule("Fibs");
        fibsModule.defineAnnotatedMethods(Fibs.class);

        RubyClass sequenceClass = runtime.defineClassUnder("Sequence", runtime.getObject(), SEQUENCE_ALLOCATOR, fibsModule);
        sequenceClass.defineAnnotatedMethods(Sequence.class);
    }
    
    private static final ObjectAllocator SEQUENCE_ALLOCATOR = new ObjectAllocator() {
        public IRubyObject allocate(Ruby runtime, RubyClass klazz) {
            return new Sequence(runtime, klazz);
        }
    };

    @JRubyClass(name="Sequence", parent="Object")
    public static class Sequence extends RubyObject {

	@JRubyMethod(meta=true)
        public static IRubyObject add(ThreadContext context, IRubyObject self, IRubyObject a, IRubyObject b) {
             return JavaEmbedUtils.javaToRuby(context.runtime,a.convertToInteger().getLongValue()+b.convertToInteger().getLongValue());
        }

        private final ReferenceQueue queue;
	Ruby runtime;
        public Sequence(Ruby runtime, RubyClass klass) {
            super(runtime, klass);
	    this.runtime = runtime;
            queue = new ReferenceQueue();
        }

        public ReferenceQueue getQueue() {
            return queue;
        }

        @JRubyMethod
        public IRubyObject poll(IRubyObject a, IRubyObject b) {
            return JavaEmbedUtils.javaToRuby(runtime,a.convertToInteger().getLongValue()+b.convertToInteger().getLongValue());
        }

        @JRubyMethod
        public IRubyObject remove() {
            return null;
        }

        @JRubyMethod
        public IRubyObject remove(IRubyObject timeout) {
            return null;
        }

        private IRubyObject returnable(Object result) {
            
            return null;
        }
    }

    
}
