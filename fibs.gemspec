# fibs.gemspec

Gem::Specification.new do |s|
  s.name    = "fibs"
  s.version = "0.4.0"
  s.summary = "Sample Jruby Extension"
  s.author  = "Chamila Wijayarathna"

  files = Dir.glob("ext/**/*.{c,java,rb}") +
          Dir.glob("lib/**/*.rb") + 
	  Dir.glob("src/**/*.{java}")

  if RUBY_PLATFORM =~ /java/
    s.platform = "java"
  else
    s.extensions << "ext/faye_websocket/extconf.rb"
  end

  s.files = files

  s.add_development_dependency "rake-compiler"
end
