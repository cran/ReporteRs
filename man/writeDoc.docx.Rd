\name{writeDoc.docx}
\alias{writeDoc.docx}
\title{Write a docx object in a docx file}
\usage{
\method{writeDoc}{docx}(doc, file, ...)
}
\arguments{
  \item{doc}{Object of class \code{\link{docx}} that has to
  be written.}

  \item{file}{single character value, name of the file to
  write.}

  \item{...}{further arguments, not used.}
}
\description{
Write the \code{\link{docx}} object in a '.docx' file.
}
\examples{
\donttest{
# Create a new document
doc = docx( title = "title" )

doc = addParagraph(doc, "Hello Word!", stylename = "Normal")

# Write the object in file "writeDoc_example.docx"
writeDoc( doc, "writeDoc_example.docx" )
}
}
\seealso{
\code{\link{docx}}, \code{\link{writeDoc}}
}

