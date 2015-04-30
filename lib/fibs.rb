module Fibs
    if RUBY_PLATFORM =~ /java/
      require File.expand_path('../fibs.jar', __FILE__)
      Java::org.jruby.ext.FibsLibrary.new.load(JRuby.runtime, false)
      def self.mask(*args)
        @mask ||= Sequence.new
        @mask.mask(*args)
      end
    end
 end
