package org.icspl.ecdtlp.models

data class PreviewModel(val tsNo: String, val type: String, val tsCh: String, val tsLoc: String,
                        val cpMin: String, val cpMax: String, val casMin: String, val casMax: String,
                        val valve: String, val p1: String, val p2: String, val acPSP: String,
                        val zinc: String, val date: String, val time: String, val manRemark: String,
                        val tlpFile: String, val readingFile: String, val selfieFile: String,
                        val startKmFile: String, val endKmFile: String, val vehicalNoFile: String
)