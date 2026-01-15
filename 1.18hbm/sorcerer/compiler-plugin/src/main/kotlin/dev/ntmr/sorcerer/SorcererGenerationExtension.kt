/*
 SPDX-FileCopyrightText: 2024 MartinTheDragon <martin@ntmr.dev>
 SPDX-License-Identifier: GPL-3.0-or-later
 */

package dev.ntmr.sorcerer

import org.jetbrains.kotlin.GeneratedDeclarationKey
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension

class SorcererGenerationExtension(
    session: FirSession
) : FirDeclarationGenerationExtension(session) {
    private data object SorcererDeclarationKey : GeneratedDeclarationKey()

//    override fun generateFunctions(callableId: CallableId, context: MemberGenerationContext?): List<FirNamedFunctionSymbol> {
//        buildSimpleFunction {
//            symbol = FirNamedFunctionSymbol(callableId)
//            origin = SorcererDeclarationKey.origin
//            returnTypeRef = session.builtinTypes.booleanType
//        }

//        val symbol = createMemberFunction(context!!.owner, SorcererDeclarationKey, callableId.callableName, session.builtinTypes.booleanType.type) {
//
//        }.apply {
//            replaceBody(buildBlock {
//                statements.add(
//                    buildFunctionCall {
//                        calleeReference = buildSimpleNamedReference {
//                            name = Name.identifier("kotlin.io.println")
//                        }
//                        argumentList = buildResolvedArgumentList(buildArgumentList {
//                            arguments.add(
//                                buildLiteralExpression(
//                                    source = null,
//                                    kind = ConstantValueKind.String,
//                                    "Test",
//                                    setType = true
//                                )
//                            )
//                        }, LinkedHashMap())
//                    }
//                )
//                statements.add(
//                    buildReturnExpression {
//                        target = FirFunctionTarget(null, false)
//                        result = buildLiteralExpression(source = null, kind = ConstantValueKind.Boolean, false, setType = true)
//                    }
//                )
//            })
//        }.symbol
//        return listOf(symbol)
//    }

//    override fun getCallableNamesForClass(classSymbol: FirClassSymbol<*>, context: MemberGenerationContext): Set<Name> {
//        return setOf(Name.identifier("testfjkdsajkasdf"))
//    }
}
