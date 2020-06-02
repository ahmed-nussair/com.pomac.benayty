package com.pomac.benayty.model.response;

import com.google.gson.annotations.SerializedName;
import com.pomac.benayty.model.FcmSendResult;

import java.util.List;

public class FcmSendResponse {

    @SerializedName("multicast_id")
    private long multiCastId;

    private int success;

    private int failure;

    @SerializedName("canonical_ids")
    private int canonicalIds;

    private List<FcmSendResult> results;

    public long getMultiCastId() {
        return multiCastId;
    }

    public int getSuccess() {
        return success;
    }

    public int getFailure() {
        return failure;
    }

    public int getCanonicalIds() {
        return canonicalIds;
    }

    public List<FcmSendResult> getResults() {
        return results;
    }
}
