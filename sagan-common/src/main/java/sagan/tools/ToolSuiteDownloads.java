package sagan.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ToolSuiteDownloads {

    private final Map<String, ToolSuitePlatform> platforms;
    private final List<UpdateSiteArchive> archives;
    private final String shortName;
    private final String releaseName;
	private final String whatsNew;

    public ToolSuiteDownloads(String shortName, String releaseName, String whatsNew,
                              Map<String, ToolSuitePlatform> platforms, List<UpdateSiteArchive> archives) {
        this.shortName = shortName;
        this.releaseName = releaseName;
        this.platforms = platforms;
        this.archives = archives;
	    this.whatsNew = whatsNew;
    }

    public List<ToolSuitePlatform> getPlatformList() {
        ArrayList<ToolSuitePlatform> platformList = new ArrayList<>();
        platformList.add(platforms.get("windows"));
        platformList.add(platforms.get("mac"));
        platformList.add(platforms.get("linux"));
        return platformList;
    }

    public List<UpdateSiteArchive> getArchives() {
        return archives;
    }

    public Set<DownloadLink> getPreferredDownloadLinks() {
        Set<DownloadLink> links = new HashSet<>();
        addLinks(links, "windows", "exe");
        addLinks(links, "windows", "exe");
        addLinks(links, "mac", "dmg");
        addLinks(links, "mac", "dmg");
        addLinks(links, "linux", "tar.gz");
        addLinks(links, "linux", "tar.gz");
        return links;
    }

    private void addLinks(Set<DownloadLink> links, String platformString, String fileType) {
        ToolSuitePlatform platform = platforms.get(platformString);
        if (platform == null)
            return;

        EclipseVersion eclipseVersion = platform.getEclipseVersions().get(0);
        for (Architecture architecture : eclipseVersion.getArchitectures()) {
            for (DownloadLink link : architecture.getDownloadLinks()) {
                if (link.getFileType().equals(fileType)) {
                    links.add(link);
                }
            }
        }
    }

    public String getReleaseName() {
        return releaseName;
    }

    public String getDisplayName() {
        return String.format("%s %s", shortName, releaseName);
    }

	public String getWhatsNew() {
		return whatsNew;
	}

	public boolean hasPlatforms() {
        return platforms.size() > 0;
    }
}
