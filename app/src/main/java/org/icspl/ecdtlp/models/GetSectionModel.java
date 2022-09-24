
package org.icspl.ecdtlp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSectionModel {

    @SerializedName("true")
    @Expose
    private List<SectionTrue> _true = null;

    public List<SectionTrue> getTrue() {
        return _true;
    }

    public void setTrue(List<SectionTrue> _true) {
        this._true = _true;
    }

    @Override
    public String toString() {
        return "SectionTrue: "+getTrue();
    }


    public class SectionTrue {

        @SerializedName("Section")
        @Expose
        private String section;

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        @Override
        public String toString() {
            return "Section: "+getSection();
        }
    }


}
