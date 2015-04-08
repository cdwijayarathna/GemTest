# Rakefile

spec = Gem::Specification.load('fibs.gemspec')

if RUBY_PLATFORM =~ /java/
  require 'rake/javaextensiontask'
  Rake::JavaExtensionTask.new('fibs', spec)
else
  require 'rake/extensiontask'
  Rake::ExtensionTask.new('faye_websocket', spec)
end
