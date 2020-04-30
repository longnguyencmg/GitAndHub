package com.tolo.app.remote.mapper

import com.tolo.app.data.model.GithubLicense
import com.tolo.app.remote.model.License


class LicenseEntityMapper : EntityMapper<License, GithubLicense> {

    override fun mapFromRemote(type: License): GithubLicense {
        return GithubLicense(
            type.key,
            type.name,
            type.url
        )
    }
}