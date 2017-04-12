package org.sagebionetworks.bridge.data;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

/**
 * This is the model for info.json
 */
class ArchiveInfo {
    private static final int DEFAULT_SCHEMA_REVISION = 1;

    public static class FileInfo {
        @SerializedName("filename")
        final String filename;
        @SerializedName("timestamp")
        final DateTime timestamp;

        FileInfo(String filename, DateTime timestamp) {
            this.filename = filename;
            this.timestamp = timestamp;
        }

        @Override public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FileInfo fileInfo = (FileInfo) o;
            return Objects.equal(filename, fileInfo.filename) &&
                    Objects.equal(timestamp, fileInfo.timestamp);
        }

        @Override public int hashCode() {
            return Objects.hashCode(filename, timestamp);
        }

        @Override public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("filename", filename)
                    .add("timestamp", timestamp)
                    .toString();
        }
    }

    @SerializedName("appVersion")
    String appVersion;
    @SerializedName("phoneInfo")
    String phoneInfo;

    @SerializedName("files")
    List<FileInfo> files;

    // used for surveys
    @SerializedName("surveyGuid")
    String surveyGuid;
    @SerializedName("surveyCreatedOn")
    DateTime surveyCreatedOn;

    // used for non-survey activities
    @SerializedName("item")
    String item;
    @SerializedName("schemaRevision")
    int schemaRevision = DEFAULT_SCHEMA_REVISION;

    boolean isSurvey() {
        return !isNullOrEmpty(surveyGuid) && (surveyCreatedOn != null);
    }

    boolean isSchema() {
        return !isNullOrEmpty(item);
    }

    boolean isValid() {
        return (isSurvey() ^ isSchema())
                && !isNullOrEmpty(appVersion)
                && !isNullOrEmpty(phoneInfo);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArchiveInfo that = (ArchiveInfo) o;
        return schemaRevision == that.schemaRevision &&
                Objects.equal(appVersion, that.appVersion) &&
                Objects.equal(phoneInfo, that.phoneInfo) &&
                Objects.equal(files, that.files) &&
                Objects.equal(surveyGuid, that.surveyGuid) &&
                Objects.equal(surveyCreatedOn, that.surveyCreatedOn) &&
                Objects.equal(item, that.item);
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(appVersion, phoneInfo, files, surveyGuid, surveyCreatedOn, item, schemaRevision);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("appVersion", appVersion)
                .add("phoneInfo", phoneInfo)
                .add("files", files)
                .add("surveyGuid", surveyGuid)
                .add("surveyCreatedOn", surveyCreatedOn)
                .add("item", item)
                .add("schemaRevision", schemaRevision)
                .toString();
    }
}
