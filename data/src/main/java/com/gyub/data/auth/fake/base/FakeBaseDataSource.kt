package com.gyub.data.auth.fake.base

import com.gyub.network.model.base.BaseResponse

/**
 * 기본 응답 Fake Data Source
 *
 * @author   Gyub
 * @created  2024/06/23
 */
object FakeBaseDataSource {

    val successResponse = BaseResponse(
        code = 200,
        message = "success"
    )

    val errorResponse = BaseResponse(
        code = 500,
        message = "error"
    )
}