# Rakefile
=begin
spec = Gem::Specification.load('fibs.gemspec')

if RUBY_PLATFORM =~ /java/
  require 'rake/javaextensiontask'
  Rake::JavaExtensionTask.new('fibs', spec)
else
  require 'rake/extensiontask'
  Rake::ExtensionTask.new('faye_websocket', spec)
end
=end

require 'ant'

directory "pkg/classes"

desc "Clean up build artifacts"
task :clean do
  rm_rf "pkg/classes"
  rm_rf "lib/fibs.jar"
end

desc "Compile the extension"
task :compile => "pkg/classes" do |t|
  ant.javac :srcdir => "ext", :destdir => t.prerequisites.first,
    :source => "1.5", :target => "1.5", :debug => true,
    :classpath => "${java.class.path}:${sun.boot.class.path}"
end

desc "Build the jar"
task :jar => :compile do
  ant.jar :basedir => "pkg/classes", :destfile => "lib/fibs.jar", :includes => "**/*.class"
end

