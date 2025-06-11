<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:date="http://xml.apache.org/xalan/java/java.util.Date"
                xmlns:sdf="http://xml.apache.org/xalan/java/java.text.SimpleDateFormat">
    <xsl:output encoding="UTF-8" indent="yes" method="xml" standalone="no" omit-xml-declaration="no"/>
    <xsl:template match="/">
        <fo:root language="EN">

            <fo:layout-master-set>
                <fo:simple-page-master
                        master-name="PaymentTransactionTemplate"
                        page-width="297mm" page-height="210mm"
                        margin-top="1cm" margin-bottom="1cm"
                        margin-left="1cm" margin-right="1cm">
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="PaymentTransactionTemplate">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-weight="bold" font-size="20pt">
                        <fo:block text-align="center">
                            <fo:external-graphic src="src/main/resources/payment-success.svg"/>
                        </fo:block>

                        <fo:block text-align="center" font-size="25pt" font-weight="bold" margin-bottom="10pt">
                            Payment Transaction
                        </fo:block>

                        <fo:block space-before="10mm">
                            <fo:inline>Date: </fo:inline>
                            <xsl:variable name="formatter" select="sdf:new('yyyy-MM-dd HH:mm')"/>
                            <xsl:variable name="now" select="date:new()"/>
                            <xsl:variable name="formattedDate" select="sdf:format($formatter, $now)"/>
                            <xsl:value-of select="$formattedDate"/>
                        </fo:block>

                        <fo:block>
                            <fo:inline>Amount: </fo:inline>
                            <xsl:value-of select="paymentTransactionRequestDTO/amount"/>
                            <xsl:choose>
                                <xsl:when test="paymentTransactionRequestDTO/currency='EUR'">€</xsl:when>
                                <xsl:when test="paymentTransactionRequestDTO/currency='USD'">$</xsl:when>
                                <xsl:when test="paymentTransactionRequestDTO/currency='GBP'">£</xsl:when>
                            </xsl:choose>

                        </fo:block>
                        <fo:block>
                            <fo:inline>Status: </fo:inline>
                            <xsl:value-of select="paymentTransactionRequestDTO/status"/>
                        </fo:block>
                        <fo:block>
                            <fo:inline>Description: </fo:inline>
                            <xsl:value-of select="paymentTransactionRequestDTO/description"/>
                        </fo:block>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>




