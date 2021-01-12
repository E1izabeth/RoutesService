<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" exclude-result-prefixes="msxsl"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:msxsl="urn:schemas-microsoft-com:xslt"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/">

  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="*[not(comment() | processing-instruction() | *)][normalize-space(text()) = '']">
    <xsl:element name="{name()}" namespace="{namespace-uri()}">
      <xsl:for-each select="@* | namespace::*">
        <xsl:copy/>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>

  <xsl:template match="@* | node()">
    <xsl:copy>
      <xsl:apply-templates select="@* | node()"/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="soap:address[@location='http://127.0.0.1:8787/ws/locations']" priority="5">
    <soap:address location="http://127.0.0.1:9191/locations-service"/>
  </xsl:template>

  <xsl:template match="soap:address[@location='http://127.0.0.1:8787/ws/routes']" priority="5">
    <soap:address location="http://127.0.0.1:9191/routes-service"/>
  </xsl:template>

</xsl:stylesheet>
