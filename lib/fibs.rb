require File.expand_path('../fibs', __FILE__)

module Fibs

    if RUBY_PLATFORM =~ /java/
      require 'jruby'
      fibs.FibService.new.basicLoad(JRuby.runtime)

      def self.mask(*args)
        @mask ||= SequenceMask.new
        @mask.mask(*args)
      end
    end

 end
