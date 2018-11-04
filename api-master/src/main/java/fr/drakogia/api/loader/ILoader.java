package fr.drakogia.api.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface ILoader {

	void loadLibraries(final boolean defsToo) throws IOException;
	
	void loadLibraries(final List<File> files) throws IOException;
	
	File getNativesFolder();
	
	File getServerFolder();
	
	URL getURL(final File javaArchive) throws IOException;
	
	void addJar(final URL jarURL) throws IOException;
}
