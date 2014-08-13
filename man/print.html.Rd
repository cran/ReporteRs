\name{print.html}
\alias{print.html}
\title{print informations about an object of class \code{\link{html}}.}
\usage{
\method{print}{html}(x, ...)
}
\arguments{
  \item{x}{an object of class \code{\link{html}}}

  \item{...}{further arguments, not used.}
}
\description{
print informations about an object of class
\code{\link{html}}.
}
\examples{
\donttest{
# Create a new document
doc = html( title = "title" )
print( doc )
}
}
\seealso{
\code{\link{html}}, \code{\link{print}}
}

