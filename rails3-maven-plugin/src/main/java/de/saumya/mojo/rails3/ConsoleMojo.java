package de.saumya.mojo.rails3;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * goal to run the rails console
 * 
 * @goal console
 * @execute phase="initialize"
 */
public class ConsoleMojo extends AbstractRailsMojo {

    // override super mojo and make this readonly
    /**
     * @parameter expression="false"
     * @readonly
     */
    protected boolean fork;

    /**
     * arguments for the console command
     * 
     * @parameter default-value="${console}"
     */
    protected String  consoleArgs = null;

    @Override
    protected void executeWithGems() throws MojoExecutionException {
        // make sure the whole things run in the same process
        super.fork = false;
        final StringBuilder commandArgs = new StringBuilder("'console'");
        if (this.args != null) {
            for (final String arg : this.args.split("\\s+")) {
                commandArgs.append(",'").append(arg).append("'");
            }
        }
        if (this.consoleArgs != null) {
            for (final String arg : this.consoleArgs.split("\\s+")) {
                commandArgs.append(",'").append(arg).append("'");
            }
        }
        execute("-e ENV['GEM_HOME']='" + this.gemHome + "';ENV['GEM_PATH']='"
                + this.gemPath + "';ARGV<<[" + commandArgs
                + "];ARGV.flatten!;load('" + railsScriptFile() + "');", false);
    }
}
