
<StyledLayerDescriptor version="1.0.0"
  xsi:schemaLocation="http://www.opengis.net/sld StyledLayerDescriptor.xsd"
  xmlns="http://www.opengis.net/sld"
  xmlns:ogc="http://www.opengis.net/ogc"
  xmlns:xlink="http://www.w3.org/1999/xlink"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

  <NamedLayer>
    <Name>OCEANSEA_1M:Foundation</Name>
    <UserStyle>
       <Name>GEOSYM</Name>
       <IsDefault>1</IsDefault>
 <FeatureTypeStyle>
      <Rule>
       <LineSymbolizer>
         <Stroke>
           <CssParameter name="stroke">#FF0000</CssParameter>
           <CssParameter name="stroke-width">8</CssParameter>
           <CssParameter name="stroke-linecap">round</CssParameter>
         </Stroke>
       </LineSymbolizer>
     </Rule>
   </FeatureTypeStyle>
    <FeatureTypeStyle>
      <Rule>
       <LineSymbolizer>
       <Stroke>
           <CssParameter name="stroke">#FF0000</CssParameter>
           <CssParameter name="stroke-width">5</CssParameter>
           <CssParameter name="stroke-linecap">round</CssParameter>
         </Stroke>
       </LineSymbolizer>
      </Rule>
   </FeatureTypeStyle>
    </UserStyle>
  </NamedLayer>
</StyledLayerDescriptor>