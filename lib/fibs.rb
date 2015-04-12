module Fibs
    if RUBY_PLATFORM =~ /java/
      require File.expand_path('../fibs.jar', __FILE__)

      def self.mask(*args)
        @mask ||= Sequence.new
        @mask.mask(*args)
      end
    end
 end
