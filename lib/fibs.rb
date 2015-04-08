require File.expand_path('../fibs', __FILE__)

module Faye
  module WebSocket

    if RUBY_PLATFORM =~ /java/
      require 'jruby'
      fibs.FibService.new.basicLoad(JRuby.runtime)

      def self.mask(*args)
        @mask ||= SequenceMask.new
        @mask.mask(*args)
      end
    end

  end
end
