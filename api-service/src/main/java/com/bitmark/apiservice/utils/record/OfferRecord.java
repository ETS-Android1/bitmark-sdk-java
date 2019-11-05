/**
 * SPDX-License-Identifier: ISC
 * Copyright © 2014-2019 Bitmark. All rights reserved.
 * Use of this source code is governed by an ISC
 * license that can be found in the LICENSE file.
 */
package com.bitmark.apiservice.utils.record;

import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.Objects;

public class OfferRecord implements Record {

    private String id;

    private String from;

    private String to;

    private Record record;

    @SerializedName("extra_info")
    private Map<String, Object> extraInfo;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("open")
    private boolean isOpen;

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Record getRecord() {
        return record;
    }

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getLink() {
        return record == null ? null : record.link;
    }

    public String getSignature() {
        return record == null ? null : record.signature;
    }

    public String getOwner() {
        return record != null && record.owner != null ? record.owner : to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OfferRecord offer = (OfferRecord) o;
        return isOpen == offer.isOpen &&
                Objects.equals(id, offer.id) &&
                Objects.equals(from, offer.from) &&
                Objects.equals(to, offer.to) &&
                Objects.equals(record, offer.record) &&
                Objects.equals(extraInfo, offer.extraInfo) &&
                Objects.equals(createdAt, offer.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, record, extraInfo, createdAt, isOpen);
    }

    public static final class Record {

        String link;

        String owner;

        String signature;

        public String getLink() {
            return link;
        }

        public String getOwner() {
            return owner;
        }

        public String getSignature() {
            return signature;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Record record = (Record) o;
            return Objects.equals(link, record.link) &&
                    Objects.equals(owner, record.owner) &&
                    Objects.equals(signature, record.signature);
        }

        @Override
        public int hashCode() {
            return Objects.hash(link, owner, signature);
        }
    }
}
